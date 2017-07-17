<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket;

use Symfony\Component\Console\Exception\RuntimeException;

class ResponseFactory
{
    public static function create(string $raw): Response
    {
        $streams = explode(PHP_EOL, $raw);

        foreach ($streams as $stream) {
            $rawJson = json_decode(trim($stream), true);

            if (!isset($rawJson['type'])) {
                throw new RuntimeException('Response type not defined (' . $raw . ')');
            }
            switch (trim($rawJson['type'])) {
                case 'info':
                    return new Response\InfoMessage($rawJson['args'][0]);
                case 'error':
                    return new Response\ErrorMessage($rawJson['args'][0]);
                case 'warning':
                    return new Response\WarningMessage($rawJson['args'][0]);
                case 'plain_text':
                    return new Response\PlainTextMessage($rawJson['args'][0]);
                case 'end':
                    return new Response\End();
            }
        }
    }
}

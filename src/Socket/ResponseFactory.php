<?php
declare(strict_types = 1);
namespace Glagol\CompileClient\Socket;

use Symfony\Component\Console\Exception\RuntimeException;

class ResponseFactory
{
    public static function create(string $raw): Response
    {
        $rawJson = json_decode($raw, true);

        if (!isset($rawJson['type'])) {
            throw new RuntimeException('Response type not defined (' . $raw . ')');
        }

        switch (trim($rawJson['type'])) {
            case 'info':
                return new Response\InfoMessage($rawJson['args'][0]);
                break;
            case 'error':
                return new Response\ErrorMessage($rawJson['args'][0]);
                break;
            case 'warning':
                return new Response\WarningMessage($rawJson['args'][0]);
                break;
            case 'end':
                return new Response\End();
                break;
        }
    }
}